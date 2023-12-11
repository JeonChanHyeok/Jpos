<template>
    <header><h1>가게명: {{this.storeName}}<br>좌석: {{this.seatName}}</h1></header>
    <br>
<!-- 카테고리 바 -->
    <swiper
        :modules="modules"
        :slidesPerView="5"
        slideToClickedSlide="true"
        class="swiper"
        ref="categorySwiper"
        allowTouchMove="true"
        v-if="isLoading"
    >
<!-- 선택한 카테고리는 bold체 표시 나머지는 bold 해제 -->
        <swiper-slide @click="clickSlide(0)" v-bind:style="{fontWeight : computuedFontWeight(0)}">맨 위</swiper-slide>
        <swiper-slide v-for="(category,i) in categories.filter((w) => w.index > 0)" :key="i"
                      @click="clickSlide(category.index)"
                      v-bind:style="{fontWeight : computuedFontWeight(category.index)}">
            {{ category.categoryName }}
        </swiper-slide>
    </swiper>
<!-- 카테고리에 맞는 메뉴들 table로 표시 -->
    <div style="padding: 0">
        <h2 v-for="(category,i) in categories.filter((w) => w.index > 0)" ref="categories" :key="i"> {{ category.categoryName }}<br>
            <table>
                <thead>
                <th v-for="item in menuTableHeads">{{item}}</th>
                </thead>
                <tbody>
                <tr v-for="item in menus.filter((c) => c.categoryId === category.id)">
                    <td>{{item.menuName}}</td>
                    <td>{{item.price}}</td>
                    <td>99</td>
                </tr>
                </tbody>
            </table>
        </h2>
    </div>
</template>

<script>
// Import Swiper Vue.js components
import {Swiper, SwiperSlide} from 'swiper/vue';

// Import Swiper styles
import 'swiper/css';

export default {
    name: 'Menus',
    components: {
        Swiper,
        SwiperSlide,
    },
    // 변수 정의
    data() {
        return {
            isLoading: false, // 카테고리바가 먼저 생성되고 후에 카테고리 데이터를 파싱해오는데, 그때 자동으로 안바꿔줘서 아예 데이터 불러오기 전까지 카테고리 바 생성 미루기 위한 변수.
            storeName: "", // 가게 이름
            seatName: "", // 좌석 이름
            menuTableHeads:["이름", "가격","수량"], // 테이블 헤더
            categories: [
            ], // 카테고리들
            menus: [
            ], // 메뉴들
        }
    },
    // 메소드 정의
    methods: {
        // get 으로 데이터 파싱 /jpos/menus/가게id/좌석id 입력
        get(){
            this.axios.get("/jpos/menus/"+ this.$route.params.storeName + "/" + this.$route.params.seatName).then(res =>{
                   this.storeName = res.data.storeName;
                   this.seatName = res.data.seatName;
                   //카테고리 맨 처음에 맨 위로 버튼 만들기 위해 먼저 하나 푸쉬
                   this.categories.push({
                        index: 0,
                        categoryName: '맨 위',
                        weight: 'normal',
                   });
                   const tempJson = JSON.parse(JSON.stringify(res.data.categoryDtoList)); // 코드 가독성 올리기 위해 만든 임시 변수
                   for (var i = 1 ; i < tempJson.length + 1 ; i ++){
                       this.categories.push(tempJson.at(i-1));
                       this.categories.at(i).index = i;
                       this.categories.at(i).weight = 'normal';
                   } // 맨위로 버튼 다음에 쭉쭉 카테고리 추가
                   this.menus = JSON.parse(JSON.stringify(res.data.menuDtoList)); // 메뉴 추가
                   this.isLoading = true; // true로 변경되고 카테고리 바 생성.
            });
        },
        // 카테고리 바 클릭 시 해당 카테고리 위치로 이동 및 bold체 변경 ( 나머지 카테고리 bold 해제 ) .
        clickSlide(n) {
            for (let i = 0; i < this.categories.length; i++) {
                if (i === n) {
                    this.categories[i].weight = 'bold';
                    if(n !== 0) {
                        let loc_y = this.$refs.categories[i - 1].offsetTop;
                        window.scrollTo({
                            top: loc_y - 80,
                            behavior: "smooth"
                        });
                    }else{
                        window.scrollTo({
                            top: 0,
                            behavior: "smooth"
                        })
                    }
                } else {
                    this.categories[i].weight = 'normal';
                }
            }
        },
    },
    // 자동으로 계속 실행되고 있는 함수. 변경 감지 등에 사용
    computed: {
        swiper() {
            return this.$refs.categorySwiper.$swiper;
        },
        // clickSlide 메서드 실행 시 클릭된 카테고리를 제외하고 모두 weight값을 normal로 변경 후 이 메서드를 통해 카테고리들의 상태를 업데이트한다.
        computuedFontWeight() {
            return (index) => {
                return this.categories[index].weight;
            }
        },

    },
    // 화면 나올때 초기화 하는 부분
    created() {
        this.get();
    },
}

</script>

<style>
table{
    width: 100vw;
    text-align : left;
    text-size-adjust: auto;
    padding-top: 20px;
}
table th{
    padding : 12px;
    border-bottom: 2px solid  darkgray;
    background-color: rgba(0,0,255,0.1);
}
table td{
    padding : 12px;
}
table tr:nth-of-type(even){
    background-color: rgba(0,0,255,0.1);
}
.swiper{
    position: sticky;
    top: 0;
}

</style>