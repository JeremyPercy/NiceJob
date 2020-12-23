const { GraphQLID, GraphQLNonNull, GraphQLList } = require('graphql')
const EducationType = require('../types/education')
const EducationModel = require('../../model/Education')


module.exports = {
  type: new GraphQLList(EducationType),
  description: 'This query will search for all user Education',
  resolve (parent, args, { user }) {
    if (user) {
    return EducationModel.find({
      userId: user.id
    })
    } else 
     throw Error("you are not authorized")
  }
}