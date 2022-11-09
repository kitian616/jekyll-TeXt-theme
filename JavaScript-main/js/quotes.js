const quotes =[
    {
        qoute :"They must often change who would be constant in happiness or wisdom" ,
        author :"Confucius" ,
    },
    {
        qoute :"All you need in this life is ignorance and confidence, then success is sure" ,
        author :"Mark Twain" ,
    },
    {
        qoute :" Life is from the inside out. When you shift on the inside, life shifts on the outside" ,
        author :"Kamal Ravikant" ,
    },
    {
        qoute :"The two most important days in your life are the day you are born and the day you find out why." ,
        author :"Mark Twain" ,
    },
    {
        qoute :"If you are not willing to risk the usual, you will have to settle for the ordinary" ,
        author :"Jim Rohn" ,
    },
    {
        qoute :"Never lose sight of the face that the most important yard stick to your success is how you treat other people" ,
        author :" Barbara Bush" ,
    },
    {
        qoute :"uccess is not final; failure is not fatal: It is the courage to continue that counts" ,
        author :"Winston S. Churchill" ,
    },
    {
        qoute :" Success usually comes to those who are too busy to be looking for it" ,
        author :"Henry David Thoreau" ,
    },
    {
        qoute :"The secret of success is to do the common thing uncommonly well" ,
        author :"ohn D. Rockefeller Jr" ,
    },
    {
        qoute :"I owe my success to having listened respectfully to the very best advice, and then going away and doing the exact opposite." ,
        author :"G. K. Chesterton" ,
    },
];


const quote = document.querySelector("#quote span:first-child");
const author = document.querySelector("#quote span:last-child");
//floor 1.9는 -> 1 , random : 랜덤한 숫자를 출력하게 한다.
const todaysQuote = quotes[Math.floor(Math.random() * quotes.length)];

quote.innerText = todaysQuote.qoute;
author.innerText = todaysQuote.author;