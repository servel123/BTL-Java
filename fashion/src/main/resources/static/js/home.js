/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
$(document).ready(function() {
    let imageSources = [
        "https://th.bing.com/th/id/OIP.B5G9g_Dg_xhJXo_SlewYRwHaE8?rs=1&pid=ImgDetMain",
        "https://th.bing.com/th/id/OIP.iPttK98DzGabuztT-ALZsAHaE6?rs=1&pid=ImgDetMain",
        "https://th.bing.com/th/id/OIP.OPW4Bdh9Gzyomt-g8IbSEAHaE8?rs=1&pid=ImgDetMain",
        "https://th.bing.com/th/id/R.adaab26fbf2a21e7f77fbd974af188c1?rik=lCpZk8Qxt%2fPr5A&pid=ImgRaw&r=0"
    ];
    let currentIndex = 0;
    let autoChangeEna = true;
    let autoChangeEnaInterval, enableAutoChangeTimeOut;
    let img = $('#hero_img');
    function enableAutoChange(){
        autoChangeEna = true;
        clearTimeout(enableAutoChangeTimeOut);
        autoChangeEnaInterval = setInterval(function(){
            if(currentIndex === imageSources.length){
                currentIndex = 0;
            }
            img.attr('src', imageSources[currentIndex]);
            currentIndex++;
        }, 2500);
        
    }
    function disableAutoChange(){
        autoChangeEna = false;
        clearInterval(autoChangeEnaInterval);
        img.attr('src', imageSources[currentIndex]);
        enableAutoChangeTimeOut = setTimeout(enableAutoChange, 10000);
    }
    img.attr('src', imageSources[currentIndex]);
    currentIndex++;
    if(autoChangeEna)enableAutoChange();

    $('#hero_prev').click(function() {
        if(currentIndex === 0)currentIndex = imageSources.length - 1;
        else currentIndex -= 1;
        disableAutoChange();
    });
    $('#hero_next').click(function() {
        if(currentIndex === imageSources.length - 1)currentIndex = 0;
        else currentIndex += 1;
        disableAutoChange();

    });
//  Pop_bar
    let pop_pant_left = $('#pop_pant_left');
    let pop_pant_right = $('#pop_pant_right');
    let displayPant = $('.displayPart.pant').first();
    pop_pant_left.on('click', function(){
        displayPant.scrollLeft(displayPant.scrollLeft() - 500);
    });
    pop_pant_right.on('click', function(){
        displayPant.scrollLeft(displayPant.scrollLeft() + 500);
    });
    let pop_cate_left = $('#pop_cate_left');
    let pop_cate_right = $('#pop_cate_right');
    let displayCategory = $('.displayPart.category').first();
    pop_cate_left.on('click', function(){
        displayCategory .scrollLeft(displayCategory .scrollLeft() - 500);
    });
    pop_cate_right.on('click', function(){
        displayCategory .scrollLeft(displayCategory .scrollLeft() + 500);
    });
});

