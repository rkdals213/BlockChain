import Vue from "vue";
import Vuex from "vuex";
import VuexPersistence from 'vuex-persist';

const persistor = new VuexPersistence({
    key: 'root',
    storage: window.localStoarge
});

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        isSigned: false, // 로그인 여부
        user: {
            id: 0, // 사용자 아이디 저장
            walletAddress: null
        },
    },
    mutations: {
        setIsSigned(state, isSigned) {
            state.isSigned = isSigned;
        },
        setUserId(state, id) {
            state.user.id = id;
        },
        setWalletAddress(state, address) {
            state.user.walletAddress = address;
        },
        logout(state) {
            state.isSigned = false;
            state.user.id = 0;
            state.user.walletAddress = null;
        },
    },
    actions: {},
    modules: {
    },
    plugins: [persistor.plugin]
});
