Hello! Sorry this took longer than I thought had some family stuff come up.

Okay a couple things. I know you said to run it in docker, but running a command line application in a docker container 
didn't make a lot of sense to me, so I opted to instead just run it directly.

So to run this just run `mvn spring-boot:run`

I also took some liberties with the inputs into the command line. 

Instead of having the create process be determined by 3 inputs and the close be determined by 1 input, I gave a prompt 
to start that expects an input of `create` or `close`. This was brought on by the inputs of the create being a little awkward
as 3 strings. 

First, the parentId I felt was not a required field so I let that be optional.

Second, the description should be allowed to have spaces, so I couldn't just split on spaces and expect my inputs to make 
sense. I could have done a quoted input or comma seperated, but that made the UX feel bad so I opted to have the inputs be
seperate prompts instead.

There are plenty of other improvements I could have made throughout this project, but in the interest of time I decided to 
just handle a few edge cases to show I was thinking about them.

I hope this works well for you and give you what you need. Let me know if you need anything else!