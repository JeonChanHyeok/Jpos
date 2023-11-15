<template>
    <header><h2>돈가네김치돼지찌개/c-3</h2></header>
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
            <swiper-slide v-for="(category,i) in categories.filter((w) => w.index > 0)" :key="i"
                          @click="clickSlide(category.index)"
                          v-bind:style="{fontWeight : computuedFontWeight(category.index)}">카테고리 {{ category.index }}
            </swiper-slide>
        </swiper>
    </div>
    <div>
        <table>

        </table>
    </div>


</template>

<script>
// Import Swiper Vue.js components
import {Swiper, SwiperSlide} from 'swiper/vue';
import {Pagination} from 'swiper/modules';

// Import Swiper styles
import 'swiper/css';


export default {
    name: 'App',
    components: {
        Swiper,
        SwiperSlide,
    },
    data() {
        return {
            categories: [
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
            ],
            menus: [
                {
                    menuId: 0,
                    menuName: 'menu_A',
                    storeId: 0,
                    categoryName: '한식',
                    price: 1000,
                },
                {
                    menuId: 1,
                    menuName: 'menu_B',
                    storeId: 0,
                    categoryId: '한식',
                    price: 2000,
                },
                {
                    menuId: 2,
                    menuName: 'menu_C',
                    storeId: 0,
                    categoryId: '한식',
                    price: 3000,
                },
                {
                    menuId: 3,
                    menuName: 'menu_D',
                    storeId: 0,
                    categoryId: '일식',
                    price: 4000,
                },
                {
                    menuId: 4,
                    menuName: 'menu_E',
                    storeId: 0,
                    categoryId: '일식',
                    price: 5000,
                },
                {
                    menuId: 5,
                    menuName: 'menu_F',
                    storeId: 0,
                    categoryId: '일식',
                    price: 6000,
                },
                {
                    menuId: 6,
                    menuName: 'menu_G',
                    storeId: 0,
                    categoryId: '중식',
                    price: 7000,
                },
                {
                    menuId: 7,
                    menuName: 'menu_H',
                    storeId: 0,
                    categoryId: '중식',
                    price: 8000,
                },
                {
                    menuId: 8,
                    menuName: 'menu_I',
                    storeId: 0,
                    categoryId: '중식',
                    price: 9000,
                },
            ]
        }
    },
    methods: {
        clickSlide(n) {
            for (let i = 0; i < this.categories.length; i++) {
                if (i === n) {
                    this.categories[i].weight = 'bold';
                } else {
                    this.categories[i].weight = 'normal';
                }
            }
        }
    },
    computed: {
        swiper() {
            return this.$refs.ctegorySwiper.$swiper;
        },
        computuedFontWeight() {
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