const mongoose = require('mongoose')
const MSchema = mongoose.Schema

const applicationSchema = new MSchema({
    date: {
        type: Date,
        default: Date.now
    },
    userId: String,
    vacancyId: String
})

module.exports = mongoose.model('Application', applicationSchema)