module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
// 跨域配置
  devServer: {                //记住，别写错了devServer//设置本地默认端口  选填
    port: 8765,
    proxy: {                 //设置代理，必须填
      '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
        target: 'http://localhost:9090',     //代理的目标地址
        changeOrigin: true,              //是否设置同源，输入是的
        pathRewrite: {                   //路径重写
          '/api': ''                     //选择忽略拦截器里面的单词
        }
      }
    }
  },
// 画线
  configureWebpack: config => {
    let path = require('path')
    config.module.rules.push({
      test: path.resolve(__dirname, 'node_modules/leader-line/'),
      use: [
        {
          loader: 'skeleton-loader',
          options: {
            procedure: content => `${content}export default LeaderLine`
          }
        }
      ]
    })
  }






}




