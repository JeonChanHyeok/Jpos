<template>
    <div class="container-fluid pt-3">
        <div class="row mt-1 row-cols-6" style="height: max-content">
            <div v-for="(seat) in seats" class="col mb-2 mt-2">
                <div class="card" @click="inOrder(seat.id)" style="height: 200px;">
                    <div class="p-3 mx-4 text-bolder card-header card-text">{{ seat.seatName }}</div>
                    <div class="p-3 pt-0 card-body text" style="overflow: scroll">
                        <p class="card-text" v-html="seat.orderContent"/>
                    </div>
                    <div class="p-3 card-footer card-text">총 금액 : {{ seat.orderPrice }}원</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import AuthorsTable from "./components/AuthorsTable.vue";
import ProjectsTable from "./components/ProjectsTable.vue";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import router from "@/router";

export default {
    name: "Pos",
    components: {
        AuthorsTable,
        ProjectsTable,
    },
    data() {
        return {
            seats: [],
            posOrders: [],
            menus: []
        }
    },
    methods: {
        stompCreate() {
            const serverURL = "http://61.101.89.253:8080/ws";
            let socket = new SockJS(serverURL);
            this.stompClient = Stomp.over(socket);
            this.stompClient.connect(
                {},
                () => {
                    this.connected = true;
                    this.get();
                    this.stompClient.subscribe("/send/" + this.$store.state.storeLoginId, res => {
                        this.get();
                    });
                },
            )
        },
        get() {
            this.axios.get("/jpos/seat/" + this.$store.state.storeLoginId).then(res => {
                this.seats = JSON.parse(JSON.stringify(res.data.seatDtoList));
                this.posOrders = JSON.parse(JSON.stringify(res.data.posOrderDtoLost));
                this.menus = JSON.parse(JSON.stringify(res.data.menuDtoList));

                for (var i = 0; i < this.seats.length; i++) {
                    this.seats.at(i).orderContent = "";
                    for (var j = 0; j < this.posOrders.length; j++) {
                        if (this.seats.at(i).id === this.posOrders.at(j).seatId) {
                            this.seats.at(i).orderPrice = this.posOrders.at(j).posOrderPrice;
                            for (var k = 0; k < (this.posOrders.at(j).posOrderContent || '').split("/").length; k++) {
                                for (var l = 0; l < this.menus.length; l++) {
                                    if (((this.posOrders.at(j).posOrderContent || '').split("/")[k] || '').split(",")[0] === "" + this.menus.at(l).id) {
                                        var count = Number(((this.posOrders.at(j).posOrderContent || '').split("/")[k] || '').split(",")[1]);
                                        this.seats.at(i).orderContent += this.menus.at(l).menuName + " " + count + "개/ " + this.menus.at(l).price * count + "원<br/>"
                                    }
                                }
                            }
                        }
                    }
                }
            })
        },
        inOrder(id){
            router.push({
                name: 'InOrder',
                params: {
                    seatId: id,
                }
            });
        }
    },
    created() {
        this.stompCreate();
    }
};
</script>
<style>
.card-text{
    font-size: 10px;
}
</style>
