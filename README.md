#PPG
PPG comes from three words: "Private Password Generator", and it describes the application perfectly.

##What is PPG in a sentence?
PPG is a super safe method to make it possible to generate and exchange passwords completely secure on public channels.

##Use-case A:
Alice's Company wants to send their source codes to Bob's Company on daily basis. They will RAR it using a password. Also, Alice's Company wants to create a new Twitter account and send its password to Bob's Company. So, how to send its password safely? Answer: Using PPG!

Alice will enter a keyword in PPG like "source" and PPG will generate the corresponding password. So, Alice will encrypt the RAR file using the generated password. Now she will send this message to Bob:

"Hi Bob,
The RAR file is attached, the PPG keyword is "source" to get unRAR password, and "twitter" for Twitter account password."

##Use-case B:
Alice and Bob are friends. Alice wants to transfer Gmail account password, including her nude picture to Bob. Alice will compress password.txt and honey.jpg in a password protected RAR file. 
Now, what should be the password of this RAR, and how to tell the password to Bob through public chats? Answer: Using PPG!
Alice will enter a keyword in PPG like "pics" and PPG will generate the corresponding password. So, Alice will encrypt the RAR file using the generated password. Now she will send this message to Bob:

"Hi Bob,
The RAR file is attached, the PPG keyword is "pics" to get unRAR password."

##Use-case C:
Alice has different online accounts. She want different and unique passwords for each of them, without carrying a dictionary.

She would install PPG and choose a great PPG Secret Key. So, she can simply enter a keyword in PPG to get its corresponding password. So, when she enters "gmail", PPG will always return "1@av93Fv93JD", and another password when she enter "yahoomail". That's all.

##What both parties need to do to have a super-secure and confidential PPG?
PPG will generate a password using the entered keyword, BASED ON a secret string entered in its settings. So, both (or more) parties need to set an identical secret. The secret string can be super long, so it would be practically unbreakable. Its stored offline in the mobile device, in its storage using complicated cryptography algorithms and methods.

The security is depended to one simple thing: The secret should remain secret between parties. So, you need to exchange it offline. Alice and Bob can agree and enter a secret when they are talking to each other in a room, and enter it in their PPG settings. This is the best method ever!

##How secure is it?
As mentioned before, the PPG Secret is everything. If it be exchanged and entered offline (or through a secure channel if you are a professional), PPG is very very safe, and its wonderfully complicated to assume an attack. It needs a full access to a lot of things which makes it almost impossible remotely.

##How a break can happen?
Well, here is the list the attacker need to have:

1. Have access to the exchanged encrypted data/account
2. Have the exchanged PPG Keyword
3. Have the corresponding PPG secret
  1. Have physical access to the mobile device that PPG is installed:
    1.Have PPG application login password
      1. PPG login password is required as the secret is encrypted using this login password in the device storage. Therefore, it can not be extracted from the storage directly. The login password is not the only used factor to encrypt the stored password. We considered the attacker could get Device UUID, Salt, etc.
  2. Attack remotely:
      1. Get Device UUID
      2. Get PPG Login Password
      3. Get Application Salt
      4. Get stored encrypted secret

Practically, the remote attack is absolutely complicated and needs a root device, installed application with full permission, and its almost identical to physical access to the device. The attacker need to have a great knowledge where PPG is used with what keywords. They need to have access the exchanged encrypted data and so on.

##How to setup?
1. Download PPG
2. Initial login password is: 1234
3. Enter the settings
4. Enter a complicated and long Secret Key
5. Enter this Secret Key on your friend's mobile using an offline method: in a bar would be our suggestion!
6. Change default application pin and choose a secure one
7. That's all.