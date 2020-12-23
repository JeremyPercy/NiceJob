const { GraphQLID, GraphQLNonNull } = require('graphql')
const VacancyType = require('../types/vacancy')
const VacancyModel = require('../../model/Vacancy')


module.exports = {
  type: VacancyType,
  description: 'This query will search for a vacancy',
  args: {
      id: {
          type: GraphQLID
      }
  },
  resolve (parent, args, { user }) {
    if (user) {
    return VacancyModel.findById(
        args.id
    )
    } else 
     throw Error("you are not authorized")
  }
}