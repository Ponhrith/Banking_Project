import { createRouter, createWebHistory } from 'vue-router';
import Side_Bar from './components/Side_Bar.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/loginregister'
        },
        {
            path: '/loginregister',
            component: () => import('./components/LoginRegister.vue')
        },
        {
            path: '/sidebar',
            name: 'Side_Bar',
            component: Side_Bar
        },
    ]
});

export default router;
