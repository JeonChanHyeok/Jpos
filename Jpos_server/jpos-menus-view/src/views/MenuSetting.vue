<template>
    <div v-for="(category) in categories.filter((i) => this.categories.at(i.index).modal)" class="modal-wrap" style="background: rgba(0,0,0,0); z-index: 999;" @click="menuModalOff(category.index)">
        <div class="modal-com" @click.stop="">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">메뉴 추가</h5>
                </div>
                <div class="modal-body">
                    <input type="text" style="width: 50%"
                           class="form-control"
                           :class="getClasses('default', false)"
                           v-model="newMenuName"
                           placeholder="메뉴명">
                    <input type="number" style="width: 50%"
                           class="form-control mt-1"
                           :class="getClasses('default', false)"
                           v-model="newMenuPrice"
                           placeholder="가격">
                </div>
                <div class="modal-footer">
                    <vsud-button @click="postMenu()">추가하기</vsud-button>
                    <vsud-button @click="menuModalOff(category.index);">닫기</vsud-button>
                </div>
            </div>
        </div>
    </div>
    <div v-for="(menu) in menus.filter((i) => this.menus.at(i.index).modal)" class="modal-wrap" style="background: rgba(0,0,0,0); z-index: 999;" @click="updateMenuModalOff(menu.index)">
        <div class="modal-com" @click.stop="">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">메뉴 수정</h5>
                </div>
                <div class="modal-body">
                    메뉴명 :
                    <input type="text" style="width: 50%"
                           class="form-control"
                           :class="getClasses('default', false)"
                           v-model="updateMenuName"
                           placeholder="메뉴명">
                    카테고리 :
                    <select style="width: 50%"
                            class="form-control"
                            :class="getClasses('default', false)"
                            v-model="updateMenuCategory">
                        <option disabled value="">카테고리 선택</option>
                        <option v-for="(category) in categories" :value=category.index>{{ category.categoryName}}</option>
                    </select>
                    가격 :
                    <input type="number" style="width: 50%"
                           class="form-control mt-1"
                           :class="getClasses('default', false)"
                           v-model="updateMenuPrice"
                           placeholder="가격">
                </div>
                <div class="modal-footer">
                    <vsud-button @click="updateMenu()">수정하기</vsud-button>
                    <vsud-button @click="updateMenuModalOff(menu.index);">닫기</vsud-button>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-header pb-0 p-3">
                        <div class="row">
                            <div class="col-8 d-flex align-items-center">
                                <h6 class="mb-0">메뉴 설정</h6>
                            </div>

                        </div>
                    </div>
                    <div class="card-body p-3 pb-0 mb-0" style="overflow: scroll;">
                        <div class="row" v-for="(category) in categories">
                            <div class="row border">
                                <div class="col-1"></div>
                                <div class="col-10 text-center my-auto">
                                    {{ category.categoryName }}
                                </div>
                                <div class="col-1 text-center my-auto">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32"
                                         fill="currentColor"
                                         class="bi bi-dash-circle center" viewBox="0 0 16 16"
                                         @click="this.deleteCategory(category.index);">
                                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                        <path d="M4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8"/>
                                    </svg>
                                </div>
                            </div>
                            <div class="row row-cols-6 p-0">
                                <!--  기존 메뉴  -->
                                <div class="col border"
                                     v-for="(item) in menus.filter((c) => c.categoryId === category.id)">
                                    <div class="row">
                                        <div class="col-6 border-end">
                                            <p class="text-truncate"
                                               style="margin-bottom: 0; text-overflow:ellipsis;">
                                                {{ item.menuName }}</p>
                                            <p style="margin-bottom: 0"> {{ item.price }}</p>
                                        </div>
                                        <div class="col-3 p-0 text-center my-auto">
                                            <vsud-button @click="updateMenuModalOn(item.index, category.index)">수정</vsud-button>
                                        </div>
                                        <div class="col-3 p-0 text-center my-auto">
                                            <vsud-button @click="deleteMenu(item.index)">삭제</vsud-button>
                                        </div>
                                    </div>
                                </div>
                                <!--  메뉴 추가  -->
                                <div class="col border">
                                    <div class="row" style="height: 100%">
                                        <div class="col text-center my-auto">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25"
                                                 fill="currentColor"
                                                 class="bi bi-plus-circle" viewBox="0 0 16 16"
                                                 @click="this.addMenu(category.index);">
                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                                <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
                                            </svg>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row border">
                            <div class="col-1"></div>
                            <div class="col-10 text-center my-auto">
                                <input type="text" style="width: 80%"
                                       class="form-control"
                                       :class="getClasses('default', false)"
                                       v-model="newCategoryName"
                                       placeholder="카테고리 추가">
                            </div>
                            <div class="col-1 text-center my-auto">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor"
                                     class="bi bi-plus-circle" viewBox="0 0 16 16" @click="this.addCategory();">
                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
                                </svg>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import VsudButton from "@/components/VsudButton.vue";
import router from "@/router";

export default {
    name: "MenuSetting",
    components: {
        VsudButton,
    },
    data() {
        return {
            newCategoryName: "",
            newMenuName: "",
            newMenuCategory: "",
            menuModal: false,
            newMenuPrice: null,
            updateMenuName: "",
            updateMenuCategory: "",
            updateMenuPrice: null,
            categories: [], // 카테고리들
            menus: [], // 메뉴들
        };
    },
    methods: {
        getClasses: (size, valid) => {
            let sizeValue, isValidValue;

            sizeValue = size ? `form-control-${size}` : null;

            isValidValue = valid ? `${valid}` : "invalid";

            return `${sizeValue} ${isValidValue}`;
        },
        getMenus() {
            this.axios.get("/jpos/menuSetting/" + this.$store.state.storeLoginId).then(res => {
                this.categories = JSON.parse(JSON.stringify(res.data.categoryDtoList));
                for (var i = 0; i < this.categories.length; i++) {
                    this.categories.at(i).index = i;
                    this.categories.at(i).modal = false;
                }
                this.menus = JSON.parse(JSON.stringify(res.data.menuDtoList));
                for (var i = 0; i < this.menus.length; i++) {
                    this.menus.at(i).index = i;
                    this.menus.at(i).modal = false;
                }
            });
        },
        addCategory() {
            if(this.newCategoryName === ""){
                alert("카테고리 이름을 입력해 주세요.")
                return;
            }
            for(let category of this.categories){
                if(category.categoryName === this.newCategoryName){
                    alert("이미 같은 이름의 카테고리가 있습니다.")
                    return;
                }
            }
            const newCategory = {
                categoryName: this.newCategoryName,
                storeId: this.$store.state.storeLoginId
            }
            this.axios.post("/jpos/menuSetting/category/add", JSON.stringify(newCategory), {
                headers: {
                    "Content-Type": "application/json"
                },
            }).then((res) => {
                alert(res.data);
                this.newCategoryName = "";
                this.getMenus();
            })

        },
        deleteCategory(categoryIndex) {
            if(confirm("하위 메뉴까지 모두 삭제됩니다. 정말 삭제하시겠습니까?") === true){
                this.axios.delete("/jpos/menuSetting/category/delete/" + this.categories.at(categoryIndex).id).then(res => {
                    alert(res.data);
                    this.getMenus();
                })
            }else{
                return;
            }
        },
        addMenu(categoryIndex) {
            this.newMenuCategory = categoryIndex;
            this.categories.at(categoryIndex).modal = true;
        },
        updateMenuModalOn(menuIndex,categoryIndex) {
            this.updateMenuName = this.menus.at(menuIndex).menuName;
            this.updateMenuCategory = categoryIndex;
            this.updateMenuPrice = this.menus.at(menuIndex).price;
            this.menus.at(menuIndex).modal = true;
        },
        postMenu() {
            if(this.newMenuCategory === "" || this.newMenuName === "" || this.newMenuPrice === null){
                alert("내용을 다 채워주십시오.");
                return;
            }else{
                const newMenu = {
                    menuName: this.newMenuName,
                    storeId: this.$store.state.storeLoginId,
                    menuCategory: this.categories.at(this.newMenuCategory).id,
                    menuPrice: this.newMenuPrice
                }
                this.axios.post("/jpos/menuSetting/menu/add", JSON.stringify(newMenu),{
                    headers: {
                        "Content-Type": "application/json"
                    },
                }).then((res) => {
                    alert(res.data);
                    this.newMenuName = "";
                    this.newMenuCategory = "";
                    this.newMenuPrice = null;
                    this.getMenus();
                });
            }
        },
        updateMenu(menuIndex){
            if(this.updateMenuCategory === "" || this.updateMenuName === "" || this.updateMenuPrice === null){
                alert("내용을 다 채워주십시오.");
                return;
            }else{
                if(this.updateMenuPrice < 0){
                    alert("가격을 다시 입력해주세요.");
                    return;
                }
                const updateMenuData = {
                    menuName: this.updateMenuName,
                    menuCategory: this.categories.at(this.updateMenuCategory).id,
                    menuPrice: this.updateMenuPrice
                }
                this.axios.patch("/jpos/menuSetting/menu/update/" + this.menus.at(menuIndex).id, JSON.stringify(updateMenuData),{
                    headers: {
                        "Content-Type": "application/json"
                    },
                }).then((res) => {
                    alert(res.data);
                    this.updateMenuName = "";
                    this.updateMenuCategory = "";
                    this.updateMenuPrice = null;
                    this.getMenus();
                });
            }
        },
        deleteMenu(menuIndex) {
            if(confirm("정말 삭제하시겠습니까?") === true){
                this.axios.delete("/jpos/menuSetting/menu/delete/" + this.menus.at(menuIndex).id).then(res => {
                    alert(res.data);
                    this.getMenus();
                })
            }else{
                return;
            }
        },
        menuModalOff(categoryIndex){
          this.categories.at(categoryIndex).modal = false;
          this.newMenuName = "";
          this.newMenuCategory = "";
          this.newMenuPrice = null;
        },
        updateMenuModalOff(menuIndex){
            this.updateMenuName = "";
            this.updateMenuCategory = "";
            this.updateMenuPrice = null;
            this.menus.at(menuIndex).modal = false;
        }
    },
    mounted() {

    },
    created() {
        this.getMenus();
    },
};
</script>
<style>

th, td {
    border: 1px solid black;
}

tr {
    background-color: #ffffff;
}

</style>