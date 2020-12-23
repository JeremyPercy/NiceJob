const { GraphQLID, GraphQLString, GraphQLNonNull } = require('graphql');

const { GraphQLDate } = require('graphql-iso-date');

const ApplicationType = require('../types/application')
const ApplicationModel = require('../../model/Application')

module.exports = {
  type: ApplicationType,
  args: {
    vacancyId: {
      type: GraphQLID
    }
  },
  resolve(parent, args, {user}){
    if(user) {
      let application = new ApplicationModel({
        userId: user.id,
        vacancyId: args.vacancyId
      })
      return application.save()
    }else throw Error('you are not authorized')
  }
}