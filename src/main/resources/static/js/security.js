// // 개발자 도구(F12, Ctrl+Shift+I, Ctrl+Shift+J, Ctrl+U) 차단
// document.addEventListener("keydown", function(event) {
//     if (event.key === "F12" ||
//         (event.ctrlKey && event.shiftKey && (event.key === "I" || event.key === "J")) ||
//         (event.ctrlKey && event.key === "U")) {
//         event.preventDefault();
//         alert("개발자 도구 사용이 차단되었습니다.");
//     }
// });

// 마우스 우클릭 및 텍스트 드래그 방지
document.addEventListener("contextmenu", function(event) {
    event.preventDefault();
});
document.addEventListener("dragstart", function(event) {
    event.preventDefault();
});

// // 콘솔에서 해킹 시도 방지 (디버깅 탐지)
// (function() {
//     function blockConsole() {
//         console.log("%c 디버깅이 감지되었습니다!", "color: red; font-size: 20px;");
//         setInterval(() => {
//             debugger;
//         }, 100);
//     }
//     if (window.console) {
//         blockConsole();
//     }
// })();

// 페이지 소스 코드 숨기기 (Ctrl+U 방지)
// document.onkeydown = function(event) {
//     if (event.key === "F12" || (event.ctrlKey && event.key === "U")) {
//         return false;
//     }
// };
//
// // 자동 봇 방지 (마우스 이동 감지)
// let isHuman = false;
// document.addEventListener("mousemove", function() {
//     isHuman = true;
// });
// setInterval(function() {
//     if (!isHuman) {
//         alert("봇 감지됨! 접근이 차단됩니다.");
//         window.location.href = "about:blank";
//     }
// }, 5000);