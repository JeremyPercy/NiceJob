const mongoose = require('mongoose');
const MSchema = mongoose.Schema;

const userSchema = new MSchema({
	username: String,
	email: {
		type: String,
		unique: true
	},
	password: String,
	createdat: {
		type: Date,
		default: Date.now()
	}
})

module.exports = mongoose.model('User', userSchema)
