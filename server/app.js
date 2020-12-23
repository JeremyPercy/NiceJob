const express = require('express');
const graphqlHTTP = require('express-graphql');
const mongoose = require('mongoose');
const schema = require('./schema');
const cors = require('cors');
const expressPlayground = require('graphql-playground-middleware-express').default;
const jwt = require('jsonwebtoken')

const app = express();

if (process.env.NODE_ENV === 'development') {
  require('dotenv').config({ path: 'development.env' })
}

const SECRET = process.env.JWT_KEY

const verifyUser = async (req) => {
  const token = req.headers.authorization
  if (token) {
    try {
      const { user } = await jwt.verify(token, SECRET, {})
      req.user = user
    } catch (err) {
      console.log(err)
    }
  }
  req.next()
}

/*
mongodb://nicejob:nicejob1@ds125352.mlab.com:25352/nicejob
*/

mongoose.connect(process.env.DATABASE_URL, { 
  useCreateIndex: true,
  useNewUrlParser: true });
mongoose.connection.once('open', () => {
	console.log('yees we are connected');
});

app.use('/graphql', cors(), verifyUser, graphqlHTTP( req => ({
		graphiql: true,
		schema: schema,
		context: {
      SECRET: SECRET,
      user: req.user
		}
	})))
app.get('/playground', expressPlayground({ endpoint: '/graphql' }));

app.listen(process.env.PORT, () => {
	// localhost:4000
	console.log('Listening for request on my awesome port ' + process.env.PORT);
});
