function currency(number){
    return number.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}
$(document).ready(function(){
    $('.total').each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(currency(tmp));
    });
    $('.btn-edit').on('click', function(){
       $(this).closest('td').find('.editModal').show();
    });
    $('.edit').on('click', function(){
       $(this).closest('.editModal').hide();
    });
    $('.btn-delete').on('click', function(){
       $(this).closest('td').find('.confirmModal').show();
    });
    $('.confirm').on('click', function(){
       $(this).closest('.confirmModal').hide(); 
    });
    $('.general-table td:not(:has(button))').click(function() {
       if($(this).hasClass('expanded')){
           $(this).removeClass('expanded');  
       } else{
           $('.general-table td.expanded').removeClass('expanded');
           $(this).addClass('expanded');
       }
    });
});