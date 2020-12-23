const mongoose = require('mongoose')
const MSchema = mongoose.Schema

const companySchema = new MSchema({
    name: String,
    address: String,
    zipcode: String,
    city: String,
    email: String,
    phoneNumber: String,
    userId: String
})

module.exports = mongoose.model('Company', companySchema)