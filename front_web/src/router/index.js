import {createRouter, createWebHashHistory} from "vue-router";


const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('@/basic_views/Login.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/basic_views/register.vue')
    },
    {
        path: '/retrieve_password',
        name: 'retrieve_password',
        component: () => import('@/basic_views/retrieve_password.vue')
    },
    {
        path: '/manage',
        name: 'manage',
        component: () => import('@/manager/manage.vue'),
        redirect:'/manage/home',
        meta: {role:'Admin'},
        children:[
            {
                path:'home',
                name:'Home',
                component:()=>import('@/manager/home.vue')
            },
            {
                path: 'Information',
                name: 'information',
                component: () => import('@/manager/Information.vue')
            },
            {
                path: 'Orders',
                name:'orders',
                component:()=>import('@/manager/orders.vue')
            },
            {
                path: 'Goods',
                name:'goods',
                component:()=>import('@/manager/Goods.vue')
            },
            {
                path:'ManagerIn',
                name:'manager_in',
                component:()=>import('@/manager/ManagerIn.vue')
            },
            {
                path: 'UserIn',
                name: 'user_in',
                component:()=>import('@/manager/UserIn.vue')
            },

            {
                path:'ProductApply',
                name:'product_apply',
                component:()=>import('@/manager/ProductApply.vue')
            },
            {
                path: 'PersonalIn',
                name: 'person_in',
                component:()=>import('@/manager/PersonalIn.vue')
            },
            {
                path: 'PasswordChange',
                name: 'password_change',
                component:()=>import('@/manager/PasswordChange.vue')
            }
        ]
    },
    {
        path:'/Front',
        name:'front',
        component:()=>import('@/front/front.vue'),
        redirect:'/Front/Home',
        meta:{role:'User'},
        children: [
            {
                path: 'Home',
                name: 'f_home',
                component: () => import('@/front/Home.vue')
            },
            {
                path:'ShoppingCar',
                name: 'shopping_car',
                component:()=>import('@/front/ShoppingCar.vue')
            },
            {
                path:'Orders',
                name:'f_orders',
                component:()=>import('@/front/Orders.vue')
            },
            {
                path:'PersonalIn',
                name:'f_personal_in',
                component:()=>import('@/front/PersonalIn.vue')
            },
            {
                path:'Apply',
                name: 'apply',
                component:()=>import('@/front/Apply.vue')
            }
        ]
    }
]

const router = createRouter({
    history:createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const user =JSON.parse(localStorage.getItem('Authorization'))  ;
    if (to.path === '/'||to.path==='/register'||to.path==='/retrieve_password') {
        next();
    } else if (!user) {
        next('/');
    } else {
        const role = user["role"];
        if (role!==to.meta.role) {
            next('/');
        }  else {
            next();
        }
    }
})

export default router