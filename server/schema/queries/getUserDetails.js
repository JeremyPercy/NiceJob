const { GraphQLID, GraphQLNonNull } = require('graphql')
const UserDetailsType = require('../types/userDetails')
const UserDetailsModel = require('../../model/UserDetails')


module.exports = {
  type: UserDetailsType,
  description: 'This query will search for a user details',
  resolve (parent, args, { user }) {
    if (user) {
    return UserDetailsModel.findOne({
        userId: user.id
    })
    } else 
     throw Error("you are not authorized")
  }
}