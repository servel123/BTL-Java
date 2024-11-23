function changeCurr(number) {
  return number.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}
$(document).ready(function () {
   let billByMonth = window.billByMonth;
   let totalProductByCategory = window.totalProductByCategory;
   let totalProductSoldByCategory = window.totalProductSoldByCategory;
   let revenueByMonth = window.revenueByMonth;
   const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
   let billByMonthData = $.map(months, function(month){
       var matchingItem = $.grep(billByMonth, function(item){
           return item[0] === month;
       });
       return [["Tháng " + month.toString(), matchingItem.length > 0 ? matchingItem[0][1] : 0]];
   });
   var options1 = {
        series: [{
          name: 'Số đơn hàng',
          data: $.map(billByMonthData, function(item){
              return item[1];
          })
        }],
        chart: {
          type: 'bar',
          height: 350
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '50%'
          }
        },
        dataLabels: {
          enabled: false
        },

        xaxis: {
          categories: $.map(billByMonthData, function(item){
              return item[0];
          })
        },
        yaxis: {
          min: 0,
          max: Math.max.apply(Math, $.map(billByMonthData, function(item){
              return item[1] + 10;
          })),
          labels: {
            formatter: function(value){
                return Math.round(value);
            }
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val;
            }
          }
        },
        title: {
          text: 'Số lượng đơn hàng',
          align: 'center',
          style: {
            fontSize: '18px',
            fontFamily: 'Roboto'
          }
        }
    };
    let chart1 = new ApexCharts($("#column-chart")[0], options1);
    chart1.render();
    var options2 = {
        series: [{
          name: "Doanh thu",
          data: $.map(revenueByMonth, function(item){
              return item[1];
          })
        }],
        chart: {
          type: 'area',
          height: 350,
          zoom: {
            enabled: true
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth'
        },

        title: {
          text: 'Doanh thu',
          align: 'center',
          style: {
            fontSize: '18px',
            fontFamily: 'Roboto'

          }
        },
        labels: $.map(revenueByMonth, function(item){
            return "Tháng " + item[0].toString();
        }),
        yaxis: {
          opposite: true
        },
        legend: {
          horizontalAlign: 'left'
        }
    };
    let chart2 = new ApexCharts($("#area-chart")[0], options2);
    chart2.render();
    var options3 = {
        series: [
          {
            name: "Sản phẩm",
            data: $.map(totalProductByCategory, function(item){
                return item[1];
            })
          },
          {
            name: "Sản phẩm bán được",
            data: $.map(totalProductSoldByCategory, function(item){
                return item[1];
            })
          }
        ],
        chart: {
          height: 350,
          type: 'line',
          zoom: {
            enabled: false
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          width: 2,
          curve: 'smooth'
        },
        title: {
          text: 'Tổng sản phẩm và sản phẩm bán được của từng danh mục',
          align: 'center',
          style: {
            fontSize: '18px',
            fontFamily: 'Roboto'
          }
        },
        grid: {
          row: {
            colors: ['#f3f3f3', 'transparent'],
            opacity: 0.5
          }
        },
        xaxis: {
          categories: $.map(totalProductByCategory, function(item){
              return item[0];
          })
        },
        yaxis: [
          {
            title: {
              text: 'Sản phẩm',
              style: {
                fontSize: '15px',
                fontFamily: 'Roboto'
              }
            },
            min: 0,
            max: Math.max.apply(Math, $.map(totalProductByCategory, function(item){
                return item[1] + 10;
            })),
            labels: {
                formatter: function(value){
                    return Math.round(value);
                }
            }
          },
          {
            opposite: true,
            title: {
              text: 'Sản phẩm bán được',
              style: {
                fontSize: '15px',
                fontFamily: 'Roboto'
              }
            },
            min: 0,
            max: Math.max.apply(Math, $.map(totalProductSoldByCategory, function(item){
                return item[1] + 10;
            })),
            labels: {
                formatter: function(value){
                    return Math.round(value);
                }
            }
          }
        ],
        legend: {
          position: 'bottom',
          horizontalAlign: 'center',
          floating: false,
          offsetY: 0,
          offsetX: 0
        }
    };
    let chart3 = new ApexCharts($("#double-line-chart")[0], options3);
    chart3.render();
    $(".revenuenprofit").each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(changeCurr(tmp));
    });
});