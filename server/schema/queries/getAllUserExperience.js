const { GraphQLID, GraphQLNonNull, GraphQLList } = require('graphql')
const ExperienceType = require('../types/experience')
const ExperienceModel = require('../../model/Experience')


module.exports = {
  type: new GraphQLList(ExperienceType),
  description: 'This query will search for all user Experience',
  resolve (parent, args, { user }) {
    if (user) {
    return ExperienceModel.find({
      userId: user.id
    })
    } else 
     throw Error("you are not authorized")
  }
}