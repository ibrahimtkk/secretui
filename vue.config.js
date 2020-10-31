module.exports = {
  lintOnSave: false,
  runtimeCompiler: true,
  productionSourceMap: false,
  devServer: {
    //proxy: 'https://pda.h2hsecure.com/'
    proxy: 'http://localhost:8080',
    compress: true,
    disableHostCheck: true,   // That solved it
  },
  //pages: {
  //  index: {
  //    // entry for the page
  //    //entry: 'src/main.js',
  //    // the source template
  //    //template: 'public/index.html',
  //    // output as dist/index.html
  //    filename: 'index.html',
  //    // when using title option,
  //    // template title tag needs to be <title><%= htmlWebpackPlugin.options.title %></title>
  //    title: 'PDAccess - Proxy Driven Access',
  //    // chunks to include on this page, by default includes
  //    // extracted common chunks and vendor chunks.
  //    chunks: ['chunk-vendors', 'chunk-common', 'index']
  //  },
  //}
}
