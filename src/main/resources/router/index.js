import Vue from 'static/static/vue'
import Router from 'static/static/vue-router'

import Index from '@/Login/Index'
import home from '@/pages/home'
import Preferred from '@/pages/Preferred'
import Order from '@/pages/Order'
import SecKill from '@/pages/SecKill'
import Inform from '@/pages/Inform'
import MyMessage from '@/pages/MyMessage'


Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'index',
            component: Index
        },
        {
            path:'/home',
            name:'home',
            component:home,
            redirect: {name: "Preferred"},   //输入路由home会重定向到Preferred页面(一进来显示的页面)
            children:
                [
                    {
                        path: "/Preferred",
                        name: "Preferred",
                        component: Preferred,
                        meta: {
                            title: "优选"
                        }
                    },
                    {
                        path: "/Order",
                        name: "Order",
                        component: Order,
                        meta: {
                            title: "订单"
                        }
                    },
                    {
                        path: "/SecKill",
                        name: "SecKill",
                        component: SecKill,
                        meta: {
                            title: "秒杀"
                        },
                    },
                    {
                        path: "/Inform",
                        name: "Inform",
                        component: Inform,
                        meta: {
                            title: "店铺信息"
                        }
                    },
                    {
                        path: "/MyMessage",
                        name: "MyMessage",
                        component: MyMessage,
                        meta: {
                            title: "我的信息"
                        }
                    }
                ]
        },
    ]
})
