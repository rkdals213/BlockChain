<template>
  <section class="blog-banner-area" id="category">
        <div class="container h-100">
            <div class="blog-banner">
                <div class="text-center">
                    <h1 style="font-size:35px;">{{title}}</h1>
                    <nav aria-label="breadcrumb" class="banner-breadcrumb"></nav>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
export default {
  props: ["title", "description"]
};
</script>

<style scoped>
.blog-banner-area{height:180px;position:relative;z-index:1}@media (min-width: 1000px){.blog-banner-area{height:270px}}.blog-banner-area .blog-banner{position:absolute;top:50%;left:50%;width:100%;transform:translate(-50%, -50%)}.blog-banner-area::after{content:"";display:block;position:absolute;top:0;left:0;width:100%;height:100%;background:#f1f6f7;z-index:-1}.blog-banner-btn{border:2px solid #384aeb;padding:10px 45px}.blog-banner-btn:hover{background:#384aeb;color:#fff}.banner-breadcrumb{display:inline-block}.banner-breadcrumb .breadcrumb{background:transparent;padding:0}.banner-breadcrumb .breadcrumb-item{padding:.1rem}.banner-breadcrumb .breadcrumb-item a{text-transform:capitalize;color:#777777}.banner-breadcrumb .breadcrumb-item+.breadcrumb-item::before{color:#777777;padding-right:.1rem;content:"-"}.banner-breadcrumb .breadcrumb-item.active{color:#777777}

ul {
    display: flex;
    list-style: none;
}

.itemCard {
    padding: 12px;
}

.hero-banner {
    background: #F1F6F7;
    position: relative;
    margin-bottom: 10px
}

@media (min-width: 991px) {
    .hero-banner {
        z-index: 111
    }
}

.hero-banner::before {
    content: "";
    display: block;
    width: 5%;
    height: 100%;
    background: #384aeb;
    position: absolute;
    top: 0;
    left: 0
}

@media (min-width: 575px) {
    .hero-banner::before {
        width: 20%
    }
}

@media (min-width: 991px) {
    .hero-banner::before {
        width: 35%
    }
}

.hero-banner__img {
    position: relative;
    top: 1px
}

@media (max-width: 575px) {
    .hero-banner__content {
        padding-bottom: 50px
    }
}

@media (min-width: 1000px) {
    .hero-banner__content {
        max-width: 495px
    }
}

.hero-banner__content h4 {
    font-size: 30px;
    font-family: "Roboto", sans-serif;
    color: #555555;
    margin-bottom: 12px
}

.hero-banner__content h1 {
    font-family: "Roboto", sans-serif;
    font-size: 25px;
    text-transform: uppercase;
    font-weight: 700;
    margin-bottom: 10px
}

@media (min-width: 767px) {
    .hero-banner__content h1 {
        font-size: 30px;
        margin-bottom: 25px
    }
}

@media (min-width: 991px) {
    .hero-banner__content h1 {
        font-size: 50px
    }
}

.hero-banner__content p {
    margin-bottom: 10px
}

@media (min-width: 767px) {
    .hero-banner__content p {
        margin-bottom: 40px
    }
}
</style>