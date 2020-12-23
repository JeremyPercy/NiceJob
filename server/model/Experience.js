const mongoose = require('mongoose')
const MSchema = mongoose.Schema

const experienceSchema = new MSchema({
    companyName: String,
    experience: String,
    periodeFrom: String,
    periodeTo: String,
    userId: String
})

module.exports = mongoose.model('Experience', experienceSchema)