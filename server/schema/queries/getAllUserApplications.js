const { GraphQLID, GraphQLNonNull, GraphQLList } = require('graphql')
const ApplicationType = require('../types/application')
const ApplicationModel = require('../../model/Application')


module.exports = {
  type: new GraphQLList(ApplicationType),
  description: 'This query will search for all user Applications',
  resolve (parent, args, { user }) {
    if (user) {
    return ApplicationModel.find({
      userId: user.id
    })
    } else 
     throw Error("you are not authorized")
  }
}