const {
  GraphQLID,
  GraphQLString,
  GraphQLList
} = require('graphql')
const VacancyType = require('../types/vacancy')
const Vacancy = require('../../model/Vacancy')


module.exports = {
  type: new GraphQLList(VacancyType),
  description: 'This query will search for all vacancies',
  args: {
    search: {
      type: GraphQLString
    }
  },
  resolve(parent, args, {
    user
  }) {
    if (user) {
      return Vacancy.find({$text: {$search: args.search}})
    } else
      throw Error("you are not authorized")
  }
}