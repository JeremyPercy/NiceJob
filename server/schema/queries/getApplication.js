const { GraphQLID, GraphQLNonNull } = require('graphql')
const ApplicationType = require('../types/application')
const Application = require('../../model/Application')


module.exports = {
  type: ApplicationType,
  description: 'This query will search for a user application',
  resolve (parent, args, { user }) {
    if (user) {
    return Application.find({
        userId: user.id
    })
    } else 
     throw Error("you are not authorized")
  }
}