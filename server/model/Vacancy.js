const mongoose = require('mongoose');
const MSchema = mongoose.Schema;

const vacancySchema = new MSchema({
	title: String,
	function: String,
	description: String,
	tags: [
		{
			title: {
				type: String
			}
		}
	],
	salary: Number,
	companyId: String
})

vacancySchema.index({'$**': 'text'})

module.exports = mongoose.model('Vacancy', vacancySchema)
