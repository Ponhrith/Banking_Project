import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/loginregister',
            component: () => import('./components/LoginRegister.vue')
        },
        {
            path: '/sidebar',
            component: () => import('./components/Side_Bar.vue')
        },
    ]
});

export default router