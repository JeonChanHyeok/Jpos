<template>
    <div class="container-fluid pt-3">
        <div class="row mt-1 row-cols-6" style="height: max-content">
            <div v-for="(seat, i) in seats" class="col mb-2 mt-2" :key="i">
                <div class="card" style="height: 200px;">
                    <div class="p-3 mx-4 text-bolder card-header card-text">{{ seat.seatName }}</div>
                    <div class="p-3 pt-0 card-body text text-center align-middle">
                        <p></p>
                        <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" fill="currentColor"
                             class="bi bi-dash-circle" viewBox="0 0 16 16" @click="this.deleteSeat(i);">
                            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                            <path d="M4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8"/>
                        </svg>
                    </div>
                </div>
            </div>
            <div class="col mb-2 mt-2">
                <div class="card" style="height: 200px;">
                    <div class="p-3 mx-4 text-bolder card-header card-text">
                        <input type="text" style="width: 100%"
                               class="form-control"
                               :class="getClasses('default', false)"
                               v-model="newTableName"
                               placeholder="테이블 추가"></div>
                    <div class="p-3 pt-0 card-body text text-center align-middle">
                        <p></p>
                        <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" fill="currentColor"
                             class="bi bi-plus-circle" viewBox="0 0 16 16" @click="this.addSeat();">
                            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                            <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
                        </svg>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import router from "@/router";

export default {
    name: "TableSetting",
    data() {
        return {
            seats: [],
            newTableName: ""
        }
    },
    methods: {
        getClasses: (size, valid) => {
            let sizeValue, isValidValue;

            sizeValue = size ? `form-control-${size}` : null;

            isValidValue = valid ? `${valid}` : "invalid";

            return `${sizeValue} ${isValidValue}`;
        },
        get() {
            this.axios.get("/jpos/seat/setting/" + this.$store.state.storeLoginId).then(res => {
                this.seats = JSON.parse(JSON.stringify(res.data.seatDtoList));
            });
        },
        addSeat() {
            this.axios.post("/jpos/seat/setting/add", null, {
                params:{
                    storeLoginId: this.$store.state.storeLoginId,
                    seatName: this.newTableName,
                }
            }).then(res => {
                alert(res.data);
                this.get();
            });
        },
        deleteSeat(i) {
            this.axios.delete("/jpos/seat/setting/delete/" + this.seats.at(i).id).then(res => {
                alert(res.data);
                this.get();
            })
        },
    },
    created() {
        this.get();
    }
}
</script>

<style scoped>

</style>