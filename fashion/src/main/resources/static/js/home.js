let imageSources = [
    "https://th.bing.com/th/id/OIP.B5G9g_Dg_xhJXo_SlewYRwHaE8?rs=1&pid=ImgDetMain",
    "https://th.bing.com/th/id/OIP.iPttK98DzGabuztT-ALZsAHaE6?rs=1&pid=ImgDetMain",
    "https://th.bing.com/th/id/OIP.OPW4Bdh9Gzyomt-g8IbSEAHaE8?rs=1&pid=ImgDetMain",
    "https://th.bing.com/th/id/R.adaab26fbf2a21e7f77fbd974af188c1?rik=lCpZk8Qxt%2fPr5A&pid=ImgRaw&r=0"
];
let currentIndex = 0;
let autoChangeEna = true;
let autoChangeTime, enableAutoChangeTimeOut;
let img = $('#hero_img');
function autoChangeLoop(){
    img.fadeOut(1500, function() {
        img.attr('src', imageSources[currentIndex]);
        img.fadeIn(1500, function() {
            currentIndex++;
            if(currentIndex === imageSources.length){
                currentIndex = 0;
            }
            autoChangeTime = setTimeout(autoChangeLoop, 2500);
        });
    });
}
function enableAutoChange() {
    autoChangeEna = true;
    clearTimeout(enableAutoChangeTimeOut);
    autoChangeLoop();
}
function disableAutoChange() {
    autoChangeEna = false;
    clearTimeout(autoChangeTime);
    img.attr('src', imageSources[currentIndex]);
    enableAutoChangeTimeOut = setTimeout(enableAutoChange, 10000);
}
$(document).ready(function () {
    img.attr('src', imageSources[currentIndex]);
    currentIndex++;
    if (autoChangeEna) enableAutoChange();

    $('#hero_prev').click(function () {
        if (currentIndex === 0) currentIndex = imageSources.length - 1;
        else currentIndex -= 1;
        disableAutoChange();
    });
    $('#hero_next').click(function () {
        if (currentIndex === imageSources.length - 1) currentIndex = 0;
        else currentIndex += 1;
        disableAutoChange();

    });
    $('.price_of_product').each(function () {
        let getPrice = parseFloat($(this).html());
        $(this).html(getPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }));
    });
    //  Pop_part
    $('.pop_left').on('click', function () {
        const displayPart = $(this).closest('.display_main_content').find('.displayPart');
        displayPart.scrollLeft(displayPart.scrollLeft() - 500);
    });
    $('.pop_right').on('click', function () {
        const displayPart = $(this).closest('.display_main_content').find('.displayPart');
        displayPart.scrollLeft(displayPart.scrollLeft() + 500);
    });
});

