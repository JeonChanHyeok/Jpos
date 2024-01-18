<template>
    <div class="container-fluid pt-3">
        <div class="row mt-1 row-cols-6" style="height: max-content">
            <div v-for="(seat) in seats" class="col mb-2 mt-2">
                <div class="card" @click="inOrder(seat.id, seat.seatName)" style="height: 200px;">
                    <div class="p-3 mx-4 text-bolder card-header card-text">
                        <div class="row">
                            <div class="col-7">
                                {{ seat.seatName }}
                            </div>
                            <div class="col-5">
                                {{ seat.orderTime }}
                            </div>
                        </div>
                    </div>
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
import { EventSourcePolyfill, NativeEventSource } from "event-source-polyfill";
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
            menus: [],
            seatIndex: "",
        }
    },
    methods: {
        connectEmitter() {
            const user = JSON.parse(localStorage.getItem("accessToken"));
            const token = user?.token;
            const EventSource = EventSourcePolyfill || NativeEventSource;
            const eventSource = new EventSource('http://116.123.197.103:8080/jpos/pos/sub/' + this.$store.state.storeLoginId, {
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                withCredentials: true,
            });
            eventSource.addEventListener('posStart', evt => {
            });
            eventSource.addEventListener('pos', event => {
               this.get();
            });
        },
        get() {
            this.axios.get("/jpos/pos/" + this.$store.state.storeLoginId).then(res => {
                this.seats = JSON.parse(JSON.stringify(res.data.seatResponseList));
                this.posOrders = JSON.parse(JSON.stringify(res.data.posOrderResponseList));
                this.menus = JSON.parse(JSON.stringify(res.data.menuResponseList));

                for (let i = 0; i < this.seats.length; i++) {
                    this.seats.at(i).orderContent = "";
                    for (let j = 0; j < this.posOrders.length; j++) {
                        if (this.seats.at(i).id === this.posOrders.at(j).seatId) {
                            this.seats.at(i).orderPrice = this.posOrders.at(j).posOrderPrice;
                            if (this.posOrders.at(j).createdDate !== null) {
                                this.seats.at(i).orderTime = this.posOrders.at(j).createdDate.at(3) + " : " + this.posOrders.at(j).createdDate.at(4);
                            }
                            for (let k = 0; k < (this.posOrders.at(j).posOrderContent || '').split("/").length; k++) {
                                for (let l = 0; l < this.menus.length; l++) {
                                    if (((this.posOrders.at(j).posOrderContent || '').split("/")[k] || '').split(",")[0] === "" + this.menus.at(l).id) {
                                        let count = Number(((this.posOrders.at(j).posOrderContent || '').split("/")[k] || '').split(",")[1]);
                                        this.seats.at(i).orderContent += this.menus.at(l).menuName + " " + count + "개/ " + this.menus.at(l).price * count + "원<br/>"
                                    }
                                }
                            }
                        }
                    }
                }
            })
        },
        inOrder(id, name) {
            alert(id);
            router.push({
                name: 'InOrder',
                state: {
                    seatId: id,
                    seatName: name,
                }
            });
        },
    },
    mounted() {
        this.connectEmitter();
        this.get();
    },
};
</script>
<style>
.card-text {
    font-size: 10px;
}
</style>
