const mongoose = require('mongoose')
const MSchema = mongoose.Schema

const tagSchema = new MSchema({
    title: String
})

module.exports = mongoose.model('Tag', tagSchema)