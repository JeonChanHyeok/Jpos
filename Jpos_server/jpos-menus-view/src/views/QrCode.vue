<template>
    <div class="container-fluid pt-3">
        <div class="row mt-1 row-cols-6" style="height: max-content">
            <div v-for="(seat) in seats" class="col mb-2 mt-2">
                <div class="card" style="height: 200px;">
                    <div class="p-3 mx-4 text-bolder card-header card-text">{{ seat.seatName }}</div>
                    <div class="p-3 pt-0 card-body">
                        <img :src="`https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl=http://116.123.197.103:8080/qrOrder/` + this.storeId + `/` + seat.id"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "QrCode",
    components: {

    },
    data(){
        return {
            storeId: null,
            seats:[],
        }
    },
    methods: {
        get(){
            this.axios.get("/jpos/store/qr/" + this.$store.state.storeLoginId).then(res => {
                this.storeId = res.data.storeId;
                this.seats = JSON.parse(JSON.stringify(res.data.seatDtoList));
            });
        }
    },
    created() {
        this.get();
    },
}
</script>

<style scoped>

</style>