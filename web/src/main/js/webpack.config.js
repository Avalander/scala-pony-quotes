const webpack = require('webpack')

const config = require('./scalajs.webpack.config')

config.module.rules = [{
    test: /\.js/,
    use: [ 'scalajs-friendly-source-map-loader' ],
    enforce: 'pre',
}]

config.plugins = [
	new webpack.HotModuleReplacementPlugin(),
	new webpack.NamedModulesPlugin(),
	new webpack.SourceMapDevToolPlugin({}),
	new webpack.DefinePlugin({
		VERSION: JSON.stringify(Date.now()),
		$VERSION$: JSON.stringify(Date.now()),
	})
]
config.devServer = {
	compress: true,
	hot: true,
	stats: 'minimal',
	historyApiFallback: {
		index: '/index.html',
	},
	proxy: {
		'/quote': 'http://localhost:8081',
	},
}

module.exports = config
