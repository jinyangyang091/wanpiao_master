var clientWidth = document.documentElement.clientWidth,
    swiper_ul = $('.swiper-ul'),
    lis = $('li', swiper_ul),
    img1s = $('li .img-1', swiper_ul),
    img2s = $('li div .img-2', swiper_ul),
    img2_1s = $('li div .img-2-1', swiper_ul),
    img2_2s = $('li div .img-2-2', swiper_ul),
    left = $('.left'),
    center = $('.center'),
    right = $('.right');


    lis.css('height', Math.floor(clientWidth * 0.412) + 'px')



var start = 0,
    move = 0,
    end = 0,
    moveNum = 0,
    colorList = $.map(img1s, function (el, index) { // 获取页面的信息
        var obj1 = {
            fisrtUrl: $(el).attr('src'),
            children: [{
                twoUrl: $(img2s).eq(index).attr('src'),
                two_1: $(img2_1s).eq(index).text(),
                two_2: $(img2_2s).eq(index).text(),
            }]
        }
        return obj1
    });



swiper_ul.on('touchstart', function (e) { // 开始
    start = e.changedTouches[0].pageX;
})
swiper_ul.on('touchmove', function (e) { // 移动中
    move = e.changedTouches[0].pageX;
    moveNum = start - move
    // console.log(moveNum);

    if (moveNum > 0) { // 右

        left.css({
            'left': moveNum / 1.1,
            'transform': 'scale(' + (0.8 + moveNum / 1000) + ')',
            'z-index': '2'
        })

        center.css({
            'left': moveNum / 1.2,
            'transform': 'scale(' + (1 - moveNum / 1000) + ')',
        })

        right.css({
            'left': -moveNum,
            'z-index': '1'
        })



    }
    if (moveNum < 0) { // 左

        left.css({
            'left': -moveNum + 50,
            'z-index': '1'
        })

        center.css({
            'left': moveNum / 1.1,
            'transform': 'scale(' + (1 + moveNum / 1000) + ')',
        })

        right.css({
            'left': moveNum / 1.1,
            'transform': 'scale(' + (0.8 - moveNum / 1000) + ')',
            'z-index': '2'
        })

    }
    // console.log(e);
})
swiper_ul.on('touchend', function (e) { // 结束
    if (moveNum > 0) { // 右
        colorList.unshift(colorList.pop())
        init()
    }
    if (moveNum < 0 && moveNum != 0) { // 左
        colorList.push(colorList.shift())
        init()
    }
    moveNum = 0
})

function init() {
    left.css({
            'left': '0',
            'transform': 'scale(0.8)',
        })
        .children('.img-1').attr('src', colorList[0].fisrtUrl).end()
        .children('div')
        .children('.img-2').attr('src', colorList[0].children[0].twoUrl)
        .siblings('.img-2-1').text(colorList[0].children[0].two_1)
        .siblings('.img-2-2').text(colorList[0].children[0].two_2)

    center.css({
            'left': '0',
            'transform': 'scale(1)',
        })
        .children('.img-1').attr('src', colorList[1].fisrtUrl).end()
        .children('div')
        .children('.img-2').attr('src', colorList[1].children[0].twoUrl)
        .siblings('.img-2-1').text(colorList[1].children[0].two_1)
        .siblings('.img-2-2').text(colorList[1].children[0].two_2)

    right.css({
            'left': '0',
            'transform': 'scale(0.8)',
        })
        .children('.img-1').attr('src', colorList[2].fisrtUrl).end()
        .children('div')
        .children('.img-2').attr('src', colorList[2].children[0].twoUrl)
        .siblings('.img-2-1').text(colorList[2].children[0].two_1)
        .siblings('.img-2-2').text(colorList[2].children[0].two_2)
}


























































// var swiper = $('.swiper')// 获得根标签
// var ul = $('ul', swiper)// 获得ul标签
// var lis = $('li', ul) // 获得所有的 li 标签


// // 结构数据
// var swiperMsgLis = [{
//         id: '2',
//         bigImgUrl: './img/swiper/timg (45).jpg',
//         list: {
//             smallImgUrl: './img/swiper/img_2@2x.png',
//             videoTitle: 'Alibaba',
//             videoMsg: '520.19'
//         }
//     },
//     {
//         id: '1',
//         bigImgUrl: './img/swiper/timg (38).jpg',
//         list: {
//             smallImgUrl: './img/swiper/img_1@2x.png',
//             videoTitle: '霍比特人',
//             videoMsg: '540.19'
//         }
//     },
//     {
//         id: '3',
//         bigImgUrl: './img/swiper/40214816956.jpg',
//         list: {
//             smallImgUrl: './img/swiper/img_3@2x.png',
//             videoTitle: '奇异博士',
//             videoMsg: '450.19'
//         }
//     }
// ]

// // 点击事件绑定
// $.each(lis, function (index, item) {
//     var className = $(item).attr('data-option')
//     $(item).on('click', function () {
//         switch (className) {
//             case 'left':
//                 prevImg()// 操作数据
//                 init()// 渲染数据
//                 break;
//             case 'right':
//                 nextImg()// 操作数据
//                 init()// 渲染数据
//                 break;
//         }
//     })
// });

// // 操作数据变化
// // 下一张
// function nextImg() {
//     swiperMsgLis.unshift(swiperMsgLis.pop())
// }
// // 上一张
// function prevImg() {
//     swiperMsgLis.push(swiperMsgLis.shift())
// }

// // 初始化
// // 渲染数据到页面
// function init() {
//     $.each(swiperMsgLis, function (index, item) {
//         $(lis[index])
//             .children('img').attr('src', item.bigImgUrl)
//             .end()
//             .children('div')
//             .children('img').attr('src', item.list.smallImgUrl)
//             .end()
//             .children('span:nth-of-type(1)').text(item.list.videoTitle)
//             .end()
//             .children('span:nth-of-type(2)').text(item.list.videoMsg)
//     });
// }
// init()