const clock = document.querySelector("h2#clock");
//document를 이용하여 html의 clock부분에 접근하자.

function getClock(){
    const date = new Date(); // 현재시간을 나타낸다.
    const hours = String(date.getHours()).padStart(2,"0");
    const minutes = String(date.getMinutes()).padStart(2,"0");
    const seconds = String(date.getSeconds()).padStart(2,"0");
    //.padStart(2,"0") - 2자리수가 아니면 앞에 0을 붙인다.
    // String(date.getHours()) - 정수형을 문자형으로 변경해준다. String을 감싸주면 된다.
   clock.innerText = `${hours}:${minutes}:${seconds}`;
}

getClock(); // 함수를 호출하자 근데 계속 시간이 흐를려면 setInterval을 사용!!!
setInterval(getClock, 1000); // 1초마다