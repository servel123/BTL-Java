$(document).ready(function () {
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
//    qui JS 
    const slider = $('#slider');
    const thumbMin = $('#thumb-min');
    const thumbMax = $('#thumb-max');
    const sliderRange = $('#slider-range');
    const minPriceDisplay = $('#min-price-display');
    const maxPriceDisplay = $('#max-price-display');
    const minPriceInput = $('#min-price');
    const maxPriceInput = $('#max-price');
 //   Giá trị default
    const minPrice = 0;
    const maxPrice = 1000000;
    let currentMin = 0;
    let currentMax = 1000000;
    function updateThumbs(){
        const sliderWidth = slider.width();
        const minPercent = ((currentMin - minPrice) / (maxPrice - minPrice)) * 100;
        const maxPercent = ((currentMax - minPrice) / (maxPrice - minPrice)) * 100;
        thumbMin.css('left', `${minPercent}%`);
        thumbMax.css('left', `${maxPercent}%`);
        sliderRange.css('left', `${minPercent}%`);
        sliderRange.css('width', `${maxPercent - minPercent}%`);
        minPriceDisplay.html(currentMin.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }));
        maxPriceDisplay.html(currentMax.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }));
 //       Cập nhật giá trị input ẩn
        minPriceInput.val(currentMin);
        maxPriceInput.val(currentMax);
    }
    function moveThumb(evt, thumb, isMin){
        const sliderRect = slider[0].getBoundingClientRect();
        const sliderWidth = sliderRect.width;
        const offsetX = Math.min(Math.max(0, evt.clientX - sliderRect.left), sliderWidth);
        const price = Math.round(minPrice + ((offsetX / sliderWidth) * (maxPrice - minPrice)));
        if(isMin){
            if(price < currentMax){
                currentMin = Math.max(minPrice, price);
            } 
        } else{
            if(price > currentMin){
                currentMax = Math.min(maxPrice, price);
            }
        }
         updateThumbs();
     }
     thumbMin.on('mousedown', function(){
         $(document).on('mousemove', function(evt){
             moveThumb(evt, thumbMin, true);
         });
         $(document).on('mouseup', function(){
             $(document).off('mousemove');
         });
     });
     thumbMax.on('mousedown', function(){
         $(document).on('mousemove', function(evt){
             moveThumb(evt, thumbMax, false);
         });
         $(document).on('mouseup', function(){
             $(document).off('mousemove');
         });
     });
     updateThumbs();
});

