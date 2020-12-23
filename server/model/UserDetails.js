const mongoose = require('mongoose')
const MSchema = mongoose.Schema

const userDetailsSchema = new MSchema({
    name: String,
    lastName: String,
    address: String,
    zipcode: String,
    city: String,
    email: String,
    phoneNumber: String,
    userId: String
})

module.exports = mongoose.model('UserDetails', userDetailsSchema)