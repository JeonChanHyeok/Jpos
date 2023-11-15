<template>
    <header><h1>가게이름/자리번호 (돈가네김치찌개/c-3)</h1></header>
    <div>나중에 빈공간</div>
    <div>
        <swiper
                :modules="modules"
                :slidesPerView="5"
                slideToClickedSlide="true"
                class="swiper"
                ref="ctegorySwiper"
                allowTouchMove="true"
        >
            <swiper-slide @click="clickSlide(0)" v-bind:style="{fontWeight : computuedFontWeight(0)}">전체</swiper-slide>
            <swiper-slide v-for="(category,i) in categories.filter((w) => w.index > 0)" :key="i" @click="clickSlide(category.index)" v-bind:style="{fontWeight : computuedFontWeight(category.index)}">카테고리 {{category.index}}</swiper-slide>
        </swiper>
    </div>
    <div>

    </div>


</template>

<script>
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from 'swiper/vue';
import { Pagination } from 'swiper/modules';

// Import Swiper styles
import 'swiper/css';


export default {
    name: 'App',
    components: {
        Swiper,
        SwiperSlide,
    },
    data() {
        return{
            categories:[
                {
                    index: 0,
                    weight: 'normal',
                },
                {
                    index: 1,
                    weight: 'normal',
                },
                {
                    index: 2,
                    weight: 'normal',
                },
                {
                    index: 3,
                    weight: 'normal',
                },
                {
                    index: 4,
                    weight: 'normal',
                },
                {
                    index: 5,
                    weight: 'normal',
                },
                {
                    index: 6,
                    weight: 'normal',
                },
                {
                    index: 7,
                    weight: 'normal',
                },
            ],
        }
    },
    methods: {
        clickSlide(n){
            for (let i = 0; i < this.categories.length ; i++){
                if( i === n ){
                    this.categories[i].weight = 'bold';
                }else{
                    this.categories[i].weight = 'normal';
                }
            }
        }
    },
    computed: {
        swiper() {
            return this.$refs.ctegorySwiper.$swiper;
        },
        computuedFontWeight(){
            return (index) => {
                return this.categories[index].weight;
            }
        },
    },
    setup() {
        return {
            modules: [Pagination],
        };
    },
}

</script>

<style>
</style>