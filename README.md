# SecurityDemo
这个工程主包下面有DES，3DES，AES，RSA，MD5，SHA加密工具，详情看README
# Attention  
加密类的代码都在app包中的com.example.wdh.securitydemo.utils下。  
其中SHA和MD5工具类的加密后结果跟网上验证效果一致。  
由于填充方式等参数的不同，DES，3DES，AES的结果跟互联网中现有的在线加密解密工具的结果不同，使用时和后台约定好即可。  
另外此处对于DES，3DES和AES的密钥长度没做处理，合理情况下需要处理成合法的密钥长度。  
##对称加密
包含DES，3DES和AES加密类，这里需要注意输入密钥的长度。如果用以处理字符串，输出结果会编码成Base64格式。  
##非对称加密
RSA类，未做验证  
##单向加密
MD5和SHA加密。其中SHA类可感觉传入字符串的不同才用不到的算法，可选SHA1，SHA256，SHA384，SHA512。在单向加密算法中，对字符串输出结果做了处理，详情请看代码。
