function changeCurr(number) {
  return number.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

var options1 = {
  series: [{
    name: 'Số đơn hàng',
    data: [
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100),
      Math.floor(Math.random() * 100)
    ]
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
    categories: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12']
  },
  yaxis: {
    min: 0,
    max: 100
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
var options2 = {
  series: [{
    name: "Doanh thu",
    data: [10000, 15000, 12000, 18000, 18500, 17000, 10000, 15000, 12000, 18000, 18500, 17000]
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
    curve: 'straight'
  },

  title: {
    text: 'Doanh thu',
    align: 'center',
    style: {
      fontSize: '18px',
      fontFamily: 'Roboto'

    }
  },
  labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
  yaxis: {
    opposite: true
  },
  legend: {
    horizontalAlign: 'left'
  }
};

var options3 = {
  series: [
    {
      name: "Sản phẩm",
      data: [100, 150, 120, 180, 185, 170, 100, 150, 120, 180, 185, 170]
    },
    {
      name: "Sản phẩm bán được",
      data: [2000, 3500, 2800, 4200, 4800, 3900, 2000, 3500, 2800, 4200, 4800, 3900]
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
    curve: 'straight'
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
    categories: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12']
  },
  yaxis: [
    {
      title: {
        text: 'Sản phẩm',
        style: {
          fontSize: '15px',
          fontFamily: 'Roboto'
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
$(document).ready(function () {
  let chart1 = new ApexCharts($("#column-chart")[0], options1);
  chart1.render();
  let chart2 = new ApexCharts($("#area-chart")[0], options2);
  chart2.render();
  let chart3 = new ApexCharts($("#double-line-chart")[0], options3);
  chart3.render();
  $(".revenuenprofit").each(function(){
      let tmp = parseFloat($(this).html());
      $(this).html(changeCurr(tmp));
  });
});