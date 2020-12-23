const mongoose = require('mongoose')
const MSchema = mongoose.Schema

const applySchema = new MSchema({
    date: {
        type: Date,
        default: Date.now()
    },
    userId: String,
    vacancyId: String
})

module.exports = mongoose.model('Apply', applySchema)