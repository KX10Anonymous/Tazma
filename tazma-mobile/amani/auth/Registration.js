import React, { createRef, useState } from 'react';
import {
  Image,
  KeyboardAvoidingView,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View
} from 'react-native';

const RegisterScreen = (props) => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const [userPhone, setUserPhone] = useState('');
  const [gender, setGender] = useState(undefined);
  const [userPassword, setUserPassword] = useState('');
  const [role, setRole] = useState(undefined);
  const [loading, setLoading] = useState(false);
  const [errortext, setErrortext] = useState('');
  const [isRegistrationSuccess, setIsRegistrationSuccess] = useState(false);

  const genderData = [
    { label: 'Male', value: 'MALE' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Other', value: 'UNISEX' },
  ];
  const roleData = [
    { label: 'Client', value: 'CLIENT' },
    { label: 'Stylist', value: 'STYLIST' },
  ];

  const handleRoleSelection = (value) => {
    setRole(value);
  };

  const handleGenderSelection = (value) => {
    setGender(value);
  };

  const surnameInputRef = createRef();
  const emailInputRef = createRef();
  const phoneInputRef = createRef();
  const passwordInputRef = createRef();

  const handleSubmitButton = () => {
    setErrortext('');
    if (!firstName) {
      alert('Please fill First Name');
      return;
    }
    if (!lastName) {
      alert('Please fill Surname');
      return;
    }
    if (!userEmail) {
      alert('Please fill Email');
      return;
    }
    if (!userPhone) {
      alert('Please fill Phone');
      return;
    }
    if (!gender) {
      alert('Please select Gender');
      return;
    }
    if (!role) {
      alert('Please select Role');
      return;
    }
    if (!userPassword) {
      alert('Please fill Password');
      return;
    }
    //Show Loader
    setLoading(true);
    var dataToSend = {
      firstname: firstName,
      lastname: lastName,
      email: userEmail,
      role: role,
      phone: phone,
      password: userPassword,
      gender: gender
    };
    var formBody = [];
    for (var key in dataToSend) {
      var encodedKey = encodeURIComponent(key);
      var encodedValue = encodeURIComponent(dataToSend[key]);
      formBody.push(encodedKey + '=' + encodedValue);
    }
    formBody = formBody.join('&');

    fetch('http://localhost:8080/tazma/api/auth/register', {
      method: 'POST',
      body: formBody,
      headers: {
        //Header Definition
        'Content-Type': 'application/json',
      },
    })
      .then((response) => response.json())
      .then((responseJson) => {
        //Hide Loader
        setLoading(false);
        
        localStorage.setItem("jwt", data.jwt);
        // If server response message same as Data Matched
        if (responseJson.status === 'success') {
          setIsRegistrationSuccess(true);
          console.log('Registration Successful. Please Login to proceed');
        } else {
          setErrortext(responseJson.msg);
        }
      })
      .catch((error) => {
        //Hide Loader
        setLoading(false);
        console.error(error);
      });
  };
  if (isRegistrationSuccess) {
    return (
      <View
        style={{
          flex: 1,
          backgroundColor: '#ffffff',
          justifyContent: 'center',
        }}>
        <Image
          //source={require('../Image/success.png')}
          style={{
            height: 150,
            resizeMode: 'contain',
            alignSelf: 'center',
          }}
        />
        <Text style={styles.successTextStyle}>Registration Successful</Text>
        <TouchableOpacity
          style={styles.buttonStyle}
          activeOpacity={0.5}
          onPress={() => props.navigation.navigate('HomeScreen')}>
          <Text style={styles.buttonTextStyle}>Login Now</Text>
        </TouchableOpacity>
      </View>
    );
  }
  return (
    <View style={{ flex: 1, backgroundColor: 'white',marginTop:20 }}>
      <ScrollView
        keyboardShouldPersistTaps="handled"
        contentContainerStyle={{
          justifyContent: 'center',
          alignContent: 'center',
          padding: 10,
        }}>
        <View style={{ alignItems: 'center' }}>
          <Image
            source={require('../assets/fotor.png')}
            style={{
              width: '100%',
              height: 300,
              resizeMode: 'contain',
              margin: 30,
            }}
          />
        </View>
        <KeyboardAvoidingView enabled>
          <View style={styles.SectionStyle}>
            <TextInput
              style={styles.inputStyle}
              onChangeText={(FirstName) => setFirstName(FirstName)}
              underlineColorAndroid="#f000"
              placeholder="Enter First Name"
              placeholderTextColor="#8b9cb5"
              autoCapitalize="sentences"
              returnKeyType="next"
              onSubmitEditing={() =>
                surnameInputRef.current && surnameInputRef.current.focus()
              }
              blurOnSubmit={false}
            />
          </View>
          <View style={styles.SectionStyle}>
            <TextInput
              style={styles.inputStyle}
              onChangeText={(LastName) => setLastName(LastName)}
              underlineColorAndroid="#f000"
              placeholder="Enter Last Name"
              placeholderTextColor="#8b9cb5"
              autoCapitalize="sentences"
              returnKeyType="next"
              ref={surnameInputRef}
              onSubmitEditing={() =>
                emailInputRef.current && emailInputRef.current.focus()
              }
              blurOnSubmit={false}
            />
          </View>
          <View style={styles.SectionStyle}>
            <TextInput
              style={styles.inputStyle}
              onChangeText={(UserEmail) => setUserEmail(UserEmail)}
              underlineColorAndroid="#f000"
              placeholder="Enter Email"
              placeholderTextColor="#8b9cb5"
              keyboardType="email-address"
              ref={emailInputRef}
              returnKeyType="next"
              onSubmitEditing={() =>
                phoneInputRef.current && phoneInputRef.current.focus()
              }
              blurOnSubmit={false}
            />
          </View>
          <View style={styles.SectionStyle}>
            <TextInput
              style={styles.inputStyle}
              onChangeText={(Phone) => setPhone(Phone)}
              underlineColorAndroid="#f000"
              placeholder="Enter Phone"
              placeholderTextColor="#8b9cb5"
              keyboardType="numeric"
              ref={phoneInputRef}
              returnKeyType="next"
              onSubmitEditing={() =>
                passwordInputRef.current && passwordInputRef.current.focus()
              }
              blurOnSubmit={false}
            />
          </View>
          <View style={styles.SectionStyle}>
            <TextInput
              style={styles.inputStyle}
              onChangeText={(UserPassword) => setUserPassword(UserPassword)}
              underlineColorAndroid="#f000"
              placeholder="Enter Password"
              placeholderTextColor="#8b9cb5"
              ref={passwordInputRef}
              returnKeyType="next"
              secureTextEntry={true}
              blurOnSubmit={false}
            />
          </View>

          <View style={styles.roleContainer}>
            {roleData.map((roleItem, index) => (
              <TouchableOpacity
                key={index}
                style={[
                  styles.roleButton,
                  role === roleItem.value && styles.roleButtonSelected,
                ]}
                onPress={() => handleRoleSelection(roleItem.value)}>
                <Text
                  style={[
                    styles.roleButtonText,
                    role === roleItem.value && styles.roleButtonTextSelected,
                  ]}>
                  {roleItem.label}
                </Text>
              </TouchableOpacity>
            ))}
          </View>
          {/* Gender buttons */}
          <View style={styles.genderContainer}>
            {genderData.map((genderItem, index) => (
              <TouchableOpacity
                key={index}
                style={[
                  styles.genderButton,
                  gender === genderItem.value && styles.genderButtonSelected,
                ]}
                onPress={() => handleGenderSelection(genderItem.value)}>
                <Text
                  style={[
                    styles.genderButtonText,
                    gender === genderItem.value &&
                      styles.genderButtonTextSelected,
                  ]}>
                  {genderItem.label}
                </Text>
              </TouchableOpacity>
            ))}
          </View>

          {errortext != '' ? (
            <Text style={styles.errorTextStyle}>{errortext}</Text>
          ) : null}
          <TouchableOpacity
            style={styles.buttonStyle}
            activeOpacity={0.5}
            onPress={handleSubmitButton}>
            <Text style={styles.buttonTextStyle}>REGISTER</Text>
          </TouchableOpacity>
        </KeyboardAvoidingView>
      </ScrollView>
    </View>
  );
};


const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#34282C',
  },
  SectionStyle: {
    flexDirection: 'row',
    height: 40,
    marginTop: 0,
    marginLeft: 35,
    marginRight: 35,
    margin: 5,
  },
  buttonStyle: {
    backgroundColor: '#7DE24E',
    borderWidth: 0,
    color: '#FFFFFF',
    borderColor: '#7DE24E',
    height: 40,
    alignItems: 'center',
    borderRadius: 30,
    marginLeft: 35,
    marginRight: 35,
    marginTop: 10,
    marginBottom: 20,
  },
  buttonTextStyle: {
    color: '#FFFFFF',
    paddingVertical: 10,
    fontSize: 16,
  },
  inputStyle: {
    flex: 1,
    color: '#fff',
    paddingLeft: 15,
    paddingRight: 15,
    borderWidth: 1,
    borderRadius: 30,
    borderColor: '#dadae8',
  },
  errorTextStyle: {
    color: 'red',
    textAlign: 'center',
    fontSize: 14,
  },
  successTextStyle: {
    color: 'white',
    textAlign: 'center',
    fontSize: 18,
    padding: 30,
  },
  roleContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    marginTop: 10,
    marginBottom: 10,
    marginLeft: 35,
    marginRight: 35,
  },

  roleButton: {
    backgroundColor: '#dadae8',
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 30,
    flexBasis: '48%',
    marginBottom: 10,
  },

  roleButtonSelected: {
    backgroundColor: '#006A4E',
  },

  roleButtonText: {
    color: '#8b9cb5',
  },

  roleButtonTextSelected: {
    color: 'white',
  },
  genderContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    marginTop: 10,
    marginBottom: 10,
    marginLeft: 10,
    marginRight: 10,
  },

  genderButton: {
    backgroundColor: '#dadae8',
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 30,
    marginRight: 10,
    flexBasis: '30%',
    marginBottom: 10,
  },

  genderButtonSelected: {
    backgroundColor: '#006A4E',
  },

  genderButtonText: {
    color: '#8b9cb5',
    textAlign: 'center',
  },

  genderButtonTextSelected: {
    color: 'white',
  },logoContainer: {
    position: 'sticky',
    top: 0,
    zIndex: 1,
    backgroundColor: '#34282C',
  },
});

export default RegisterScreen;
