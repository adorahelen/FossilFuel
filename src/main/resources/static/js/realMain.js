    // 📌 GitHub 데이터 가져오기
    function fetchContributions() {
    const username = document.getElementById("username").value;
    if (!username) {
    alert("GitHub 아이디를 입력해주세요.");
    return;
}

    fetch(`/graphql/user/${username}`)
    .then(response => response.json())
    .then(data => {
    displayContributions(data);
    saveCommitData(username, data);
})
    .catch(error => {
    console.error('Error fetching contributions:', error);
    alert('기여도 데이터를 불러오지 못했습니다.');
});
}

    function displayContributions(data) {
    const resultDiv = document.getElementById("result");
    resultDiv.innerHTML = '';
    const weeks = data.data.user.contributionsCollection.contributionCalendar.weeks;

    weeks.forEach(week => {
    week.contributionDays.forEach(day => {
    const dayDiv = document.createElement('div');
    dayDiv.classList.add('contribution-day');
    if (day.contributionCount > 0) {
    dayDiv.classList.add('active');
}
    resultDiv.appendChild(dayDiv);
});
});
}

    function saveCommitData(username, data) {
    let commitCount = 0;
    data.data.user.contributionsCollection.contributionCalendar.weeks.forEach(week => {
    week.contributionDays.forEach(day => {
    commitCount += day.contributionCount;
});
});

    const commitDTO = { username: username, commitCount: commitCount };

    fetch('/api/commits/save', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify(commitDTO)
}).then(() => updateRanking());
}

    function updateRanking() {
    fetch('/api/commits/ranking')
        .then(response => response.json())
        .then(data => {
            const rankList = document.querySelector(".rank-list");
            rankList.innerHTML = '';

            data.forEach((user, index) => {
                const newRank = document.createElement("li");
                newRank.innerHTML = `<span>${index + 1}. ${user.username}</span><span>${user.commitCount} commits</span>`;
                rankList.appendChild(newRank);
            });
        })
        .catch(error => console.error('Error fetching ranking:', error));
}

    window.onload = updateRanking;

// 여기까지 깃허브

    // [ 로그아웃 ]
    document.getElementById("logout-btn").addEventListener("click", function () {
        fetch("https://fossilfuel.site/api/auth/logout", {
            method: "POST",
            credentials: "include", // 쿠키 기반 인증 시 필요
            headers: { "Content-Type": "application/json" }
        })
            .then(response => {
                if (response.ok) {
                    alert("로그아웃되었습니다.");
                    window.location.href = "/"; // 로그인 페이지로 리디렉션
                } else {
                    alert("로그아웃 실패. 다시 시도해주세요.");
                }
            })
            .catch(error => console.error("로그아웃 중 오류 발생:", error));
    });


// 여기서부터  PDF(자소서)

    // 📌 PDF.js 설정
    const url = '/kangmin.pdf';
    let pdfDoc = null;
    let currentPage = 1;
    let scale = 1.5;

    pdfjsLib.GlobalWorkerOptions.workerSrc = "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.16.105/pdf.worker.min.js";

    function loadPDF() {
    pdfjsLib.getDocument(url).promise.then(pdf => {
        pdfDoc = pdf;
        document.getElementById('page-info').textContent = `1 / ${pdfDoc.numPages}`;
        renderPage(currentPage);
    }).catch(error => {
        console.error("PDF 로딩 실패:", error);
    });
}

    function renderPage(pageNumber) {
    pdfDoc.getPage(pageNumber).then(page => {
        const canvas = document.getElementById('pdf-viewer');
        const context = canvas.getContext('2d');
        const viewport = page.getViewport({ scale });

        canvas.width = viewport.width;
        canvas.height = viewport.height;

        const renderContext = { canvasContext: context, viewport };
        page.render(renderContext);

        document.getElementById('page-info').textContent = `${pageNumber} / ${pdfDoc.numPages}`;
    });
}

    function prevPage() {
    if (currentPage > 1) {
    currentPage--;
    renderPage(currentPage);
}
}

    function nextPage() {
    if (currentPage < pdfDoc.numPages) {
    currentPage++;
    renderPage(currentPage);
}
}

    window.onload = function () {
    updateRanking();
    loadPDF();
};
