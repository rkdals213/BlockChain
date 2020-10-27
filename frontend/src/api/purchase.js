import { createInstance } from "./index.js";

const instance = createInstance();

function create(id, pk, eoa, success, fail) {
    const body = {
        walletAddress: eoa,
        privateKey: pk,
    };
    instance
        .post("/api/purchases/start/" + id, JSON.stringify(body))
        .then(success)
        .catch(fail);
}

function send(id, pk, success, fail) {
    const body = {
        privateKey: pk,
    };
    instance
        .post("/api/purchases/send/" + id, JSON.stringify(body))
        .then(success)
        .catch(fail);
}

function confirm(id, pk, success, fail) {
    const body = {
        privateKey: pk,
    };
    instance
        .post("/api/purchases/confirm/" + id, JSON.stringify(body))
        .then(success)
        .catch(fail);
}

function cancel(id, pk, success, fail) {
    const body = {
        privateKey: pk,
    };
    instance
        .post("/api/purchases/cancel/" + id, JSON.stringify(body))
        .then(success)
        .catch(fail);
}

function findMySalePurchases(id, success, fail) {
    instance
        .get("/api/purchases/seller/" + id)
        .then(success)
        .catch(fail);
}

function findMyPurchases(id, success, fail) {
    instance
        .get("/api/purchases/buyer/" + id)
        .then(success)
        .catch(fail);
}

function getPurchaseByItemId(id, success, fail) {
    instance
        .get("/api/purchases/item/" + id)
        .then(success)
        .catch(fail);
}

/**
 * TODO: API 서버에 입금확인 요청
 * 입금 금액이 조건을 충족하지 않으면 500 error response
 */
function checkDeposit(id, success, fail) {
    instance
        .put("/api/purchases/" + id + "/deposit")
        .then(success)
        .catch(fail);
}

/**
 *
 * @param {Number} id purchaseId
 * @param {String} state S/C/X 중 하나
 */
function changeState(id, state, success, fail) {
    instance
        .put("/api/purchases/" + id + "/state/" + state)
        .then(success)
        .catch(fail);
}

/**
 * 거래 이력을 가져온다.
 * @param {Number} id purchaseId
 */
function getHistory(id, success, fail) {
    instance
        .get("/api/purchases/history/" + id)
        .then(success)
        .catch(fail);
}

function getDetail(id, success, fail) {
    instance
        .get("/api/purchases/getDetail/" + id)
        .then(success)
        .catch(fail);
}

export {
    create,
    findMySalePurchases,
    findMyPurchases,
    getPurchaseByItemId,
    checkDeposit,
    changeState,
    getHistory,
    getDetail,
    send,
    confirm,
    cancel,
};