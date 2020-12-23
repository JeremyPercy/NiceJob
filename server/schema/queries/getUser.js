const { GraphQLID, GraphQLNonNull } = require('graphql')
const UserType = require('../types/user')
const User = require('../../model/User')
const jwt = require('jsonwebtoken');

module.exports = {
  type: UserType,
  description: 'This query will search for a user with userId',
  resolve (parent, args, { user }) {
    if (user) {
    return User.findById(
        user.id
      )
    } else 
     throw Error("you are not authorized")
  }
}