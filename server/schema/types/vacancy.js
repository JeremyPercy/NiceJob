const CompanyType = require('./company')
const CompanyModel = require('../../model/Company')

const {
    GraphQLObjectType,
    GraphQLID,
    GraphQLString,
    GraphQLInt
  } = require('graphql')

  const VacancyType = new GraphQLObjectType({
    name: 'Vacancy',
    description: 'Vacancy of companies where users can apply.',
    fields: () => ({
      id: {
        type: GraphQLID
      },
      title: {
        type: GraphQLString
      },
      function: {
        type: GraphQLString
      },
      description: {
        type: GraphQLString
      },
      salary: {
        type: GraphQLInt
      },
      company: {
        type: CompanyType,
        resolve(parent, args) {
          return CompanyModel.findById( 
            parent.companyId
          )
        }
      }
    })
  })
  
module.exports = VacancyType