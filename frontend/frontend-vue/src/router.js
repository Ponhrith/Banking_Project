import {createRouter, createWebHistory} from 'vue-router'
import Side_Bar from './components/Side_Bar.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/loginregister',
            component: () => import('./components/LoginRegister.vue')
        },
        {
            path: '/sidebar',
            name: Side_Bar,
            component: () => import('./components/Side_Bar.vue')
        },
    ]
});

export default router