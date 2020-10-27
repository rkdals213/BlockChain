import { createInstance } from "./index.js";

const instance = createInstance();

function create(rating, success, fail) {
  instance
    .post("/api/ratings", JSON.stringify(rating))
    .then(success)
    .catch(fail);
}

function get(userId, success, fail) {
  instance
    .get("/api/ratings/" + userId)
    .then(success)
    .catch(fail);
}

function getList(success, fail) {
  instance
    .get("/api/ratings/")
    .then(success)
    .catch(fail);
}

export { create, get, getList };
