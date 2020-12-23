const mongoose = require('mongoose')
const MSchema = mongoose.Schema

const educationSchema = new MSchema({
    diploma: String,
    school: String,
    periodeFrom: String,
    periodeTo: String,
    userId: String
})

module.exports = mongoose.model('Education', educationSchema)